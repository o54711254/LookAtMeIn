docker build --no-cache -t frontend-image .

docker rm frontend-container --force

docker run -p 3000:80 -d --name frontend-container frontend-image