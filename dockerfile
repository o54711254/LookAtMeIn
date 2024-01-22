# FROM node
FROM nginx

RUN mkdir /app
WORKDIR /app
RUN mkdir ./build
ADD ./build ./build

# RUN npm install 
# RUN npm install -g serve
# RUN npm run build


# ENTRYPOINT [ "server","-s","build" ]

RUN rm /etc/nginx/conf.d/default.conf

COPY ./nginx.conf /etc/nginx/conf.d
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

