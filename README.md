This is a cloudify recipe for version 2.7+<BR>
This recipe creates a scaledb system with 2 nodes.<BR>
one is primary and the second is mirror.<BR>
The system works with hp cloud<BR>
Choose the hp-havana cloud on 13.5+ system<BR>
<B>copy the pem file to the cloudify-overrides directory in addition to the upload directory</B><BR>
The system consists of 6 machines.<BR>
2 CAS, 2SLM and 2 MySQL nodes which run on mariadb<BR>
<P>Since the default memory allocation is 15GB of memory, you will only be able to run the system with machines of size 101<BR>
unless you request a larger memory allocation<P>
for 102 size machines you will need 6*4GB=24GB of memory<BR>
for 103 size machines you will need 6*8GB=48GB of memory and so on <BR>
To run tests on a fully operational system or if you have further questions contact<BR>
info@bizkit.co.il for further details<BR>
