package com.test.assigntest

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast


class MyService : AccessibilityService() {
    private var isWhatsAppOpened = false
    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {
        val eventType = accessibilityEvent.eventType
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !isWhatsAppOpened) {
            val source = accessibilityEvent.source
            if (source != null && source.packageName == WHATSAPP_PACKAGE_NAME) {
                Toast.makeText(this, "WhatsApp Launched", Toast.LENGTH_SHORT).show()
                isWhatsAppOpened = true
            }
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val serviceInfo = serviceInfo
        serviceInfo.flags =
            serviceInfo.flags or AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS
        setServiceInfo(serviceInfo)
    }

    override fun onInterrupt() {}
    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_ALL_APPS && isWhatsAppOpened) {
            isWhatsAppOpened = false
        }
        return super.onKeyEvent(event)
    }

    companion object {
        private const val WHATSAPP_PACKAGE_NAME = "com.whatsapp"
    }
}
