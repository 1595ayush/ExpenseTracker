import dbus
bus = dbus.SessionBus()
notifications = bus.get_object('org.freedesktop.Notifications', '/org/freedesktop/Notifications')
interface = dbus.Interface(notifications, 'org.freedesktop.Notifications')
id = 4856
timeout = 2500
interface.Notify('name',id,'','Hi Raunaq','body','','',timeout)

