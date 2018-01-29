import sys
reload(sys)
sys.setdefaultencoding('utf-8')

from datetime import timedelta
from dateutil.relativedelta import relativedelta

# Note. "dt" should be timezone format if you use django
def get_midnight(dt):
    return dt - timedelta(hours=dt.hour, minutes=dt.minute, seconds=dt.second)

def get_next_4_weeks_same_day_midnight(dt):
    return get_midnight(dt + timedelta(days=28))

def get_next_week_same_day_midnight(dt):
    return get_midnight(dt + timedelta(days=7))

def get_tomorrow_midnight(dt):
    return get_midnight(dt+timedelta(days=1))

def get_next_monday_midnight(dt):
    return get_midnight(get_monday(dt+timedelta(days=7)))

def get_this_monday_midnight(dt):
    return get_midnight(get_monday(dt))

def get_this_friday_midnight(dt):
    return get_midnight(get_friday(dt))

def get_monday(dt):
    return dt - timedelta(days=dt.weekday())

def get_friday(dt):
    return dt + timedelta(days=5-dt.weekday())

def get_firstDay_month(dt):
    tmp_dt = dt - timedelta(days=dt.day - 1)
    return get_midnight(tmp_dt)

def get_month_of_next_monday(dt):
    return (dt+timedelta(days=(6-dt.weekday()))).month

def get_month_of_this_friday(dt):
    return (dt+timedelta(days=(4-dt.weekday()))).month

def get_day_of_this_friday(dt):
    return (dt+timedelta(days=(4-dt.weekday()))).day

def get_day_of_this_monday(dt):
    return (dt-timedelta(days=(dt.weekday()))).day

def get_next_month(dt):
    return (dt+relativedelta(months=+1)).month

def get_firstday_of_month(dt):
    return dt - timedelta(days=dt.day-1)

def get_lastday_of_month(dt):
    return dt + relativedelta(months=+1) - timedelta(days=dt.day)