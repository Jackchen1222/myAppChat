from django.contrib import admin
from .models import UserInfo
class UserIntoTable(admin.ModelAdmin):
    list_display = ('userid', 'password', 'nickname', 'sex', 'birthday',
                    'personalsign', 'landtime', 'registertime', )

admin.site.register(UserInfo, UserIntoTable)