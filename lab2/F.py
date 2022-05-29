import sys


n = int(sys.stdin.readline())
nums = list(map(int, input().split()))
stack = [9999999999]
i = 0
last = min(nums)
result = []
flag = True
while len(nums) > 0 and flag:
    if stack[-1] == last:
        stack.pop()
        result.append("pop")
        i -= 1
        nums.pop(i)

        if len(nums) > 0:
            last = min(nums)
        else:
            break
    else:
        if i >= len(nums):
            flag = False
            break
        stack.append(nums[i])
        i += 1
        result.append("push")
if flag:
    sys.stdout.write("\n".join(result))
else:
    sys.stdout.write("impossible")
'''
push
push
push
pop
push
pop
pop
push
pop
pop

'''
