sudo docker build --no-cache -t frontend-image .

sudo docker rm frontend-container --force

sudo docker run -p 3000:80 -d --name frontend-container frontend-image