import sys


queue = list()
n = int(sys.stdin.readline())

for i in range(n):
    line = sys.stdin.readline()
    zap = int(line.split(' ', 1)[0])

    if zap == 1:
        queue.append(int(line.split(' ', 1)[1]))
    elif zap == 2:
        queue.pop(0)
    elif zap == 3:
        queue.pop()
    elif zap == 4:
        sys.stdout.write(str(queue.index(int(line.split(' ', 1)[1]))) + str('\n'))
    else:
        sys.stdout.write(str(queue[0]) + str('\n'))
