import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

prob = []
width = []
time = []

with open('result.txt') as fp:
    file = fp.read()
    file = file.replace(' ', '')
    file = file.split(',')
    i = 0
    while i < len(file) - 3:
        prob.append(float(file[i]))
        i += 1
        width.append(int(file[i]))
        i += 1
        time.append(int(file[i]))
        i += 1

fig = plt.figure()
ax1 = fig.add_subplot(111, projection='3d')
ax1.plot(xs=prob, ys=width, zs=time)
ax1.set_xlabel('Probability')
ax1.set_ylabel('Width')
ax1.set_zlabel('Time (s)')

plt.show()