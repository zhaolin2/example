local s=""
for i=1,10 do
    s = s .. i
end
print(s)

--长括号中的不会进行转义
print([[str has \n and \r]])

print([=[str has[[]] \n and \r]=])


--nil和false为假 其他都是真
local bool=0
if bool==false then
    print("true")
else
    print(false)
end