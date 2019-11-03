from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
import os
import json
from .models import UserInfo

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