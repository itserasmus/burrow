package com.burrow.widget;

public abstract class State<T extends Widget> {
  public T widget;

  public void dispose() {}

  public State(T widget) {
    this.widget = widget;
  }
}