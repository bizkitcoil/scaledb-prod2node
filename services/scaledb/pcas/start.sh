
#$1 is the user home dir
#$2 is the context directory
#$3 is the unix user
sudo cp $2/iscale.manifest $1/iscale.manifest
echo sudo cp $2/iscale.manifest $1/iscale.manifest
echo sudo chown $3.$3 $1/iscale.manifest
sudo chown $3.$3 $1/iscale.manifest
echo perl $1/iscaledb -install_all
perl $1/iscaledb -install_all
