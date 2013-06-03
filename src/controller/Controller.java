package controller;

import java.awt.event.ActionListener;

public interface Controller extends ActionListener {
  public void update(Object aObject);
  public void setModel(Object aObject);
}
