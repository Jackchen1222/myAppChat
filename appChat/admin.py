from django.contrib import admin
from .models import UserInfo

admin.site.site_header = "叨叨管理"

class UserIntoTable(admin.ModelAdmin):
    list_display = ('userid', 'password', 'nickname', 'sex', 'birthday',
                    'personalsign', 'landtime', 'registertime', )

admin.site.register(UserInfo, UserIntoTable)
