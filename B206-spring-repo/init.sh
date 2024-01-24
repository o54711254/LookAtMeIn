sudo docker rm spring-container --force

sudo docker build --no-cache -t spring-image .

# sudo docker network create -d bridge test-bridge

sudo docker run -p 7000:80 -d --name spring-container --net test-bridge spring-image