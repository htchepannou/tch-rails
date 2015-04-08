package com.tchepannou.rails.action;

import com.tchepannou.rails.core.api.ActionController;

public class FlashActionController
    extends ActionController
{
    public void index ()
    {
        setFlash (ActionController.FLASH_NOTICE, "notice");
    }

    public void redirect ()
    {
        setFlash (ActionController.FLASH_NOTICE, "notice");
        redirectTo ("/flash/redirected");
    }
}
