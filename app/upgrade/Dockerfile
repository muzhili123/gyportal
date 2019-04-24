FROM python:2.7.14-jessie

RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple flask docker \
    && rm -rf /root/.cache/*

COPY . /upgrade

WORKDIR /upgrade

EXPOSE 10000

CMD ["python","/upgrade/app.py"]