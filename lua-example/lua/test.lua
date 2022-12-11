function test()
--    ngx.say("hello,main from idea")
    print("hello from main")
end

test()


local new_table=require("table.new")
local tab=new_table(100,0)
--tab.clear()

--if tab.isempty() then
--    print("empty table")
--end
--print(tab[1])


local version = { major = 1, minor = 1 }

version = setmetatable(version, {
    __index = {patch = 2},
    __tostring = function(t)
        return string.format("%d.%d.%d", t.major, t.minor)
    end
})
print(tostring(version))


function main()
end