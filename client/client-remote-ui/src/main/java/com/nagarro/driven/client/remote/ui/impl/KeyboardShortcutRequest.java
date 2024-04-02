package com.nagarro.driven.client.remote.ui.impl;

import java.util.List;

public class KeyboardShortcutRequest {

  private UiLocator by;
  private List<String> keys;

  public UiLocator getBy() {
    return by;
  }

  public void setBy(UiLocator by) {
    this.by = by;
  }

  public List<String> getKeys() {
    return keys;
  }

  public void setKeys(List<String> keys) {
    this.keys = keys;
  }
}
