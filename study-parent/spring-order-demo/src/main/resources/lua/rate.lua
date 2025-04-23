local current = 0
local period = tonumber(ARGV[1])
local threshold = tonumber(ARGV[2])
local key = KEYS[1]
-- 防止已存在没有过期时间的key
local expired = redis.call('ttl',key)
if tonumber(expired) == -1 then
    redis.call('del',key)
end
current = redis.call('hincrby', key,'count',1)
if tonumber(current) == 1 then
    redis.call('hset',key,'threshold',threshold)
    redis.call('hset',key,'period',period)
    redis.call('expire', key, period)
    return true
elseif tonumber(current) <= threshold then
    return true
else
    return false
end