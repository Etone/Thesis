# Add Docker repository
sudo apt-get update
cd ~
curl -fsSL get.docker.com -o get-docker.sh
sudo sh get-docker.sh

# Add user vagrant ro /docker/ group (so you dont need to use sudo docker for everything)
sudo usermod -aG docker vagrant

# Download and add docker-compose
sudo curl -L https://github.com/docker/compose/releases/download/1.21.0/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
