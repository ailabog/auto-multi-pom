package com.nagarro.driven.client.remote.ui.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/ranorex")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IRemoteUIClientAnnotated extends IRemoteUIClient {

  @GET
  @Path("/ping")
  @Override
  Response ping();

  @POST
  @Path("/switch")
  @Override
  Response switchApplication(LaunchConfiguration config);

  @POST
  @Path("/switchForm")
  @Override
  Response switchForm(UiLocator uiLocator);

  @POST
  @Path("/text")
  @Override
  Response getText(UiLocator uiLocator);

  @POST
  @Path("/click")
  @Override
  Response clickAt(UiLocator uiLocator);

  @POST
  @Path("/doubleClick")
  @Override
  Response doubleClickAt(UiLocator uiLocator);

  @POST
  @Path("/rightClick")
  @Override
  Response rightClickAt(UiLocator uiLocator);

  @POST
  @Path("/type")
  @Override
  Response type(Request request);

  @POST
  @Path("/start")
  @Override
  Response startApplication(LaunchConfiguration launchConfiguration);

  @POST
  @Path("/quit")
  @Override
  Response quitApplication();

  @POST
  @Path("/takeScreenshot")
  @Override
  Response takeScreenshot(UiLocator locator);

  @POST
  @Path("/takeScreenshotOfDesktop")
  @Override
  Response takeDesktopScreenshot();

  @POST
  @Path("/sendShortcut")
  @Override
  Response sendKeyboardShortcut(KeyboardShortcutRequest request);
}
