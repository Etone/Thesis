# Steps
1. Untar python3 tarball  
```bash
mkdir rootfs
cd rootfs
sudo tar xvf python3.tar
sudo rm python3.tar
cd ..  
```
2. chroot shell
```bash
sudo chroot rootf /bin/sh
```
Probleme:
  - Alle Prozesse, auch Prozesse außerhalb des "Containers"
  - `kill <pid>` mit PID von außerhalb des Containers funktioniert (`whami` = root)

3. Namespace /bin/sh
```bash
sudo unshare -p --mount-proc=$PWD/rootfs/proc -f chroot rootfs /bin/sh
```
Auch andere Namespaces
```bash
ls /proc/<pid der chroot shell>/ns
```
  - Eigene Shell in Namespace
  ```bash
  nsenter --pid=/proc/1429/ns/pid --mount=/proc/1429/ns/mnt \
   chroot $PWD/bento_ubuntu1710/rootfs /bin/sh
  ```
4. Mount Dateien von Host zu "Container"  
Bindmount da sonst chroot nicht greift (simlink somit nicht möglich)
```bash
mkdir $PWD/bento_ubuntu1710/readonlyfiles
cd readonlyfiles
touch hello.txt
vi hello.txt
Enter some text and save
cd ..
```
```bash
nsenter --mount=/proc/$PID/ns/mnt \
  mount --bind -o ro \
  $PWD/bento_ubuntu1710/readonlyfiles \
  $PWD/bento_ubuntu1710/rootfs/var/readonlyfiles
```
5. Cgroups. Sicherstellen, dass nicht zu viele Ressourcen allokiert werden (von container)
Memory limit, kann die Machine nicht "nuken" durch zu hohe Mem auslastung
```bash
mkdir /sys/fs/cgroup/memory/demo %erstellen einer memory cgroup demo
echo $PID > /sys/fs/cgroup/memory/demo/tasks %eintragen der "Container" Shell in cgroup
In chrooted Shell
cat /proc/self/cgroup
```
Einschränken des nutzbaren mem `echo "100000000" > /sys/fs/cgroup/memory/demo/memory.limit_in_bytes`
Kein memswap erlauben `echo "0" > /sys/fs/cgroup/memory/demo/memory.swappiness`  
**Create /dev/urandom if not there**  
Memory wasting python programm
```python
import time
f = open("/dev/urandom", "rb")
data = bytearray()
i = 0
while True:
        data.extend(f.read(10000000))
        i += 1
        print("%dmb" % (i*10,))
        time.sleep(1)
```
Result: Python Prozess, der wie root arbeitet, aber nach außen keine Prozesse abschießen kann

Weitere Schritte: Drop Capabilities (SELinux, ...)
Netzwerk
user namespaces (root rechte ohne root zu sein) `unshare --map-root-user chroot rootfs /bin/sh`
