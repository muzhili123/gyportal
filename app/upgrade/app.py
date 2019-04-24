#!/usr/bin/python
from flask import Flask, request, g
import json, docker, base64, os, sys
import docker.errors

app = Flask(__name__)

@app.before_request
def before_request():
    try:
        g.docker = docker.DockerClient(base_url='unix://var/run/docker.sock')
        g.name = ["ntc-ui", "gyportal", "ntc-server-ui"]
    except Exception as e:
        print("Error: %s" % (e.message))

@app.route('/upgrade',methods=['POST'])
def upgrade():
    try:
        info = json.loads(request.data)
        if info['name'] in g.name:
            if info['build']['status'] == "Success":
                g.docker.login(
                    registry="daocloud.io",
                    username=base64.b64decode("YnV4aWFvbW8="),
                    password=base64.b64decode("bGl1MTIzLi4u")
                )
                services = g.docker.services.get(
                    service_id="QuanXi_%s" % (info['name'])
                )
                services.update(
                    image=info['image']
                )
    except Exception as e:
        print("Error: %s" % (e.message))
        return 'Error', 500
    except docker.errors.APIError as e:
        print("Error: %s" % (e.message))
        return 'Error', 500
    except docker.errors.NotFound as e:
        print("Error: %s" % (e.message))
        return 'Error', 500
    else:
        return 'Done', 200

if __name__ == '__main__':
    # http://www.htcc.org.cn/upgrade
    if os.path.exists("/var/run/docker.sock") is False:
        print("Error: Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?")
        sys.exit(1)
    app.run(host="0.0.0.0",port=10000)