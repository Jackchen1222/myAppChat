import uuid

from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
import os
import time
import threading
import json
from .models import UserInfo
from dwebsocket.decorators import accept_websocket,require_websocket

clients = {}
count = 0

@accept_websocket
def chat_web_socket(request, username):
    global count
    global clients
    if request.is_websocket():
        print("username=", username)
        for message in request.websocket:
            if message != None:
                strMessage = message.decode('utf-8')
                print("stc type=", type(message), ", after type=", type(strMessage), strMessage)
                if clients.get(username) == None:
                    count = count + 1
                clients[username] = request.websocket
                for name in clients:
                    clients[name].send(message)



def msg_send(request):
    if request.method == 'POST':
        body = request.body.decode(encoding="utf-8")
        body = eval(body)
        username = body['username']
        msg = body['msg']
        for client in clients:
            clients[client].send(username + "/n/b" + msg)
    ret ={}
    ret['result'] = 'none'
    return JsonResponse(ret)


def registerUserInfo(request):
    registerRet = {}
    registerRet['result'] = 'failed'
    if request.method == 'POST':
        body = request.body.decode(encoding="utf-8")
        body = eval(body)
        if type(body) == type({}):
            if len(body['password']) >= 6 and body['nickname'] != None:
                print(body['sex'], body['password'], body['nickname'])
                userExist = UserInfo.objects.filter(nickname=body['nickname'])
                if len(userExist) > 0:
                    registerRet['reason'] = 'user exist'
                    return JsonResponse(registerRet)
                id = UserInfo.objects.all().count()
                print(id)
                sex = False
                if sex == 0:
                    sex = True
                UserInfo.objects.get_or_create(userid=id, sex=sex,
                                               password=body['password'],
                                               nickname=body['nickname'],
                                               birthday='2019-11-11',
                                               personalsign='无' )
                registerRet['id'] = id
                registerRet['result'] = 'success'
            else:
                registerRet['reason'] = 'unqualified'
    else:
        registerRet['reason'] = 'wrong mothod'

    return JsonResponse(registerRet)