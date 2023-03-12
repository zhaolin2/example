ngx.sleep(1)
ngx.say("done")

local shell = require("resty.shell")
local ok,err=shell.run([[echo ok]])
print(ok)