/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.repeatingalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.MenuItem
import com.example.android.common.logd
import com.example.android.common.toast


class RepeatingAlarmFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {

    // First create an intent for the alarm to activate.
    // This code simply starts an Activity, or brings it to the front if it has already
    // been created.
    val intent = Intent(context, MainActivity::class.java)
    intent.action = Intent.ACTION_MAIN
    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

    // Because the intent must be fired by a system service from outside the application,
    // it's necessary to wrap it in a PendingIntent.  Providing a different process with
    // a PendingIntent gives that other process permission to fire the intent that this
    // application has created.
    // Also, this code creates a PendingIntent to start an Activity.  To create a
    // BroadcastIntent instead, simply call getBroadcast instead of getIntent.
    val pendingIntent = PendingIntent.getActivity(
      activity,
      REQUEST_CODE, intent, 0
    )

    // There are two clock types for alarms, ELAPSED_REALTIME and RTC.
    // ELAPSED_REALTIME uses time since system boot as a reference, and RTC uses UTC (wall
    // clock) time.  This means ELAPSED_REALTIME is suited to setting an alarm according to
    // passage of time (every 15 seconds, 15 minutes, etc), since it isn't affected by
    // timezone/locale.  RTC is better suited for alarms that should be dependant on current
    // locale.

    // Both types have a WAKEUP version, which says to wake up the device if the screen is
    // off.  This is useful for situations such as alarm clocks.  Abuse of this flag is an
    // efficient way to skyrocket the uninstall rate of an application, so use with care.
    // For most situations, ELAPSED_REALTIME will suffice.
    val alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP

    // The AlarmManager, like most system services, isn't created by application code, but
    // requested from the system.
    val alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
    when (item.itemId) {
      R.id.oneTime -> {
        alarmManager.set(
          alarmType,
          SystemClock.elapsedRealtime() + 60 * 1000,
          pendingIntent
        )
        logd("One time alarm set.")
        toast(context, "A one time alarm was set.")
      }
      R.id.repeating -> {
        // setRepeating takes a start delay and period between alarms as arguments.
        // The below code fires after 15 minutes, and repeats every 15 minutes.
        alarmManager.setRepeating(
          alarmType,
          SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
          AlarmManager.INTERVAL_FIFTEEN_MINUTES,
          pendingIntent
        )
        logd("Repeating alarm set.")
        toast(context,"A repeating alarm was set.")
      }
      R.id.cancel_action -> {
        alarmManager.cancel(pendingIntent)
        logd("Alarm canceled.")
        toast(context,"The alarm was canceled.")
      }
    }

    return true
  }

  companion object {
    // This value is defined and consumed by app code, so any value will work.
    // There's no significance to this sample using 0.
    const val REQUEST_CODE = 0
  }
}
