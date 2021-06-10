FROM node:14.15.1
MAINTAINER He Zhu

ENV NODE_ENV=production

ENV HOST 172.18.55.173

RUN mkdir -p /app
COPY . /app
WORKDIR /app

EXPOSE 3000

#此为cnpm淘宝镜像
RUN npm config set registry https://registry.npm.taobao.org
RUN npm install
RUN npm i -D webpack-dev-server@2.9.7
RUN npm run build
CMD ["npm", "start"]