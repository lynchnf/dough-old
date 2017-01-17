Development Environment Setup
=============================

Follow the instructions below to checkout the project and set up a development environment.

Tested on Ubuntu 164.04 LTS (64 bit).

1. Install Java and related stuff.
  - `sudo apt-get install default-jdk`
  - `sudo apt-get install ant`
  - `sudo apt-get install maven`
  - `sudo apt-get install tomcat8`
  
2. Install Eclipse. (optional)
  - `sudo apt-get install eclipse`

3. Install MySQL.
  - `sudo apt-get install mysql-server`

6. Install dbvisualizer. (optional)
  - Browse to [dbvis.com](http://www.dbvis.com/).
  - Download Linux (DEB installer).
  - `sudo dpkg -1 dbvis_linux_x_x_x.deb`
  - `rm dbvis_linux_x_x_x.deb`

5. Install Git.
  - `sudo apt-get install git`
  - `sudo apt-get install gitk`
  - `git config --global user.name "Your Name Here"`
  - `git config --global user.email your_email@example.com`
  - `git config --global push.default simple`
  - Make sure you have ssh keys.

10. Get project source.
  - `cd ~/workspace`
  - `git clone git@github.com:lynchnf/dough.git`

4. Create database and user.
  - `mysql --user=root --password=********`
  - `create user doughuser identified by 'swordfish';`
  - `create database doughdb;`
  - `grant all on doughdb.* to doughuser;`
  - `exit`

11. Build and run project.
  - `cd ~/workspace/dough`
  - `mvn clean package`
  - `sudo mv target/dough.war /var/lib/tomcat8/webapps`
  - Browse to localhost:8080/dough
