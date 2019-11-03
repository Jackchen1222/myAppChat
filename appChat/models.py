from django.db import models

# Create your models here.
class UserInfo(models.Model):
    userid = models.CharField( u'ID号', max_length=64 )
    password = models.CharField( u'密码', max_length=64 )
    landtime = models.DateTimeField(u'登录时间', auto_now=True, null=True)
    registertime = models.DateTimeField(u'注册时间', auto_now_add=True, editable=True)
    nickname = models.CharField(u'昵称', max_length=64)
    sex = models.BooleanField(u'性别', default=True)
    birthday = models.CharField(u'生日', max_length=64 )
    personalsign = models.CharField(u'个性签名', max_length=128 )
