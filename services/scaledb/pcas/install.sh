#! /bin/bash
set -x
#$1 context directory
#$2 unix user name
#$3 pem file

#sudo yum -y install libaio.x86_64 libaio-devel.x86_64
#sudo yum -y install mlocate
#sudo updatedb
sudo apt-get install libaio1

if [$2 = "root"]; then
	homedir='/root'
else
	homedir="/home/$2"
fi

sudo cp $1/iscaledb $homedir/iscaledb
#echo "cp $1/${KeyFileName} /home/$2/${KeyFileName}"
 sudo cp $1/$3 $homedir/$3
 sudo chmod 400 $homedir/$3
#sudo cp $1/${KeyFileName} /home/$2/${KeyFileName}
# sudo chmod 400 /home/$2/${KeyFileName}
# sudo chown $2.$2 /home/$2/${KeyFileName}
# sudo chown $2.$2 /home/$2/iscaledb
#echo "cp ${homedir}/gs-files/${KeyFileName} ${homedir}/${KeyFileName}"
#sudo cp ${homedir}/gs-files/${KeyFileName} ${homedir}/${KeyFileName}
#sudo chmod 400 ${homedir}/${KeyFileName}
#sudo chown $2.$2 ${homedir}/${KeyFileName}
echo "cp ${homedir}/gs-files/$3 ${homedir}/$3"
sudo cp ${homedir}/gs-files/$3 ${homedir}/$3
sudo chmod 400 ${homedir}/$3
sudo chown $2.$2 ${homedir}/$3
sudo chown $2.$2 ${homedir}/iscaledb
# sudo chown $2.$2 $homedir/$3
# sudo chown $2.$2 $homedir/iscaledb
echo finished coping files
exit 0
