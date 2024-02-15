sudo docker rm spring-container --force

sudo docker build --no-cache -t spring-image .

sudo docker run -p 8080:80 -d --name spring-container spring-image