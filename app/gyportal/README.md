# 部署步骤

## 安装Docker
```
curl -sSL https://get.docker.com | sh
```

## 启用Swarm
```
docker swarm init
```

## 创建应用网络
```
docker network create -d overlay quanxi
```

## 部署项目
```
docker stack deploy -c quanxi.yaml QuanXi
```