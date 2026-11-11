package com.mycompany.gestionmemoire.web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ThemeStoreListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent event) {
    event.getServletContext().setAttribute(ThemeStore.CONTEXT_ATTRIBUTE, new ThemeStore());
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    event.getServletContext().removeAttribute(ThemeStore.CONTEXT_ATTRIBUTE);
  }
}
