local mp = require "resty.msgpack"

local my_data = {this = {"is",4,"test"}}
local encoded = mp.pack(my_data)
print(encoded)
local decoded = mp.unpack(encoded)