from collections import Counter

f = open("/Users/jshere/work/captcha.results.txt")
mp = {}
for line in f:
    split = line.split(',')
    user = split[0]
    if user in mp:
        if split[1] in mp[user]:
            mp[user][split[1]] += int(split[2])
        else:
            mp[user][split[1]] = int(split[2])
    else:
        mp[user] = {}
        mp[user][split[1]] = int(split[2])

failed = 0
failedThenPassed = 0
passedFirstTime = 0
neverTried = 0
presented = 0

for v in mp.values():
    if '"challenge presented"' in v and '"verification passed"' not in v and '"verification failed"' in v:
        failed += 1
    if '"challenge presented"' in v and '"verification passed"' in v and '"verification failed"' in v:
        failedThenPassed += 1
    if '"challenge presented"' in v and '"verification passed"' in v and '"verification failed"' not in v:
        passedFirstTime += 1
    if '"challenge presented"' in v and '"verification passed"' not in v and '"verification failed"' not in v:
        neverTried += 1
    if '"challenge presented"' in v:
        presented += 1

print "failed {0}, failedThenPassed {1}, passedFirstTime {2}, neverTried {3}, presented {4}".format(failed, failedThenPassed, passedFirstTime, neverTried, presented)