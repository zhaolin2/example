function index(table,key)
    if key == 'key2' then
        return "index vallue"
    else
        return nil
    end
end

mytable = setmetatable({key1 = "value1"}, {
    __index = index
})

print(mytable.key1,mytable.key2)