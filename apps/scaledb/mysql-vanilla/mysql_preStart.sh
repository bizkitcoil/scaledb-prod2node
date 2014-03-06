wget "http://www.scaledb.com/ycsb.8m.innodb.tar.gz"
sudo tar -C /var/lib/mysql/ -xvf ycsb.8m.innodb.tar.gz
sudo mkdir /var/lib/mysql/test
sudo chown mysql.mysql /var/lib/mysql/test
sudo mv /var/lib/mysql/usertable.frm /var/lib/mysql/test/
