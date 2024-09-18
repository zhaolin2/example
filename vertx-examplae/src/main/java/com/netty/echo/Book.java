package com.netty.echo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {
    String name;
    String index;
}
