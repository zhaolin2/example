/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flink.dataStream.join;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Example illustrating a windowed stream join between two data streams.
 *
 * <p>The example works on two input streams with pairs (name, grade) and (name, salary)
 * respectively. It joins the steams based on "name" within a configurable window.
 *
 * <p>The example uses a built-in sample data generator that generates
 * the steams of pairs at a configurable rate.
 */
@SuppressWarnings("serial")
public class StreamingWithWindowJoinExample {

	// *************************************************************************
	// PROGRAM
	// *************************************************************************

	public static void main(String[] args) throws Exception {
		// parse the parameters
		final ParameterTool params = ParameterTool.fromArgs(args);
		final long windowSize = params.getLong("windowSize", 2000);
		final long rate = params.getLong("rate", 3L);

		System.out.println("Using windowSize=" + windowSize + ", data rate=" + rate);
		System.out.println("To customize example, use: WindowJoin [--windowSize <window-size-in-millis>] [--rate <elements-per-second>]");

		// obtain execution environment, run this example in "ingestion time"
//		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
		env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);

		// make parameters available in the web interface
		env.getConfig().setGlobalJobParameters(params);

		// create the data sources for both grades and salaries
		DataStream<Tuple2<String, Integer>> grades = WindowJoinSampleData.GradeSource.getSource(env, rate);
		DataStream<Tuple2<String, Integer>> salaries = WindowJoinSampleData.SalarySource.getSource(env, rate);

		// run the actual window join program
		// for testability, this functionality is in a separate method.
		DataStream<Tuple3<String, Integer, Integer>> joinedStream = runWindowJoin(grades, salaries, windowSize);

		// print the results with a single thread, rather than in parallel
		joinedStream.print().setParallelism(2);

		// execute program
		env.execute("Windowed Join Example");
	}

	public static DataStream<Tuple3<String, Integer, Integer>> runWindowJoin(
			DataStream<Tuple2<String, Integer>> grades,
			DataStream<Tuple2<String, Integer>> salaries,
			long windowSize) {

		return grades.join(salaries)
				.where(new NameKeySelector())
				.equalTo(new NameKeySelector())

				.window(TumblingEventTimeWindows.of(Time.milliseconds(windowSize)))

				.apply(new JoinFunction<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple3<String, Integer, Integer>>() {

					@Override
					public Tuple3<String, Integer, Integer> join(
									Tuple2<String, Integer> first,
									Tuple2<String, Integer> second) {
						return new Tuple3<String, Integer, Integer>(first.f0, first.f1, second.f1);
					}
				});
	}

	private static class NameKeySelector implements KeySelector<Tuple2<String, Integer>, String> {
		@Override
		public String getKey(Tuple2<String, Integer> value) {
			return value.f0;
		}
	}
}
