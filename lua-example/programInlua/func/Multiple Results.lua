--s,e=string.find("hello lua users","lua")
--print(s,e)

function foo0()  end

function foo1() return "1"  end

function foo2() return "1","2" end

--x=foo2()
--print(x)

--10 1 2
x,y,z=10,foo2()
print(x,y,z)

--当方法调用不是最后一个时候 只会产生一个返回值
--1 20
x,y=foo2(),20
print(x,y)

--20 1
x,y=20,foo2()
print(x,y)


t={foo0()} -- a = {}  (an empty table)
t={foo1()} -- a = {'a'}
t={foo2()} -- a = {'a', 'b'}


print(table.unpack{10,20,30}) --> 10 20 30
a,b = table.unpack{10,20,30} -- a=10, b=20, 30 被丢弃

--获取table的长度
arr={1,2,3}
print(#arr)


t = {10, print, x = 12, k = "hi"}
for k, v in pairs(t) do
    print(k, v)
end

