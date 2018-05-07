package org.invictus.util.maptool;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import org.invictus.model.players.Client;
import org.invictus.model.players.PlayerHandler;

public class Shell extends JFrame implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	public Client c;

	public Shell(Client client) {
		this.c = client;
	}

	public Shell() {
		mouse = new Point(-1, -1);
		click = new Point(-1, -1);
	}

	public void mouseDragged(MouseEvent e) {
		MapClient.mapView = new Point((MapClient.mapView.x + e.getX()) - mouse.x, (MapClient.mapView.y + e.getY()) - mouse.y);
		if (MapClient.mapView.x > 0)
			MapClient.mapView.x = 0;
		if (MapClient.mapView.y > 0)
			MapClient.mapView.y = 0;
		if (MapClient.mapView.x < -1757)
			MapClient.mapView.x = -1757;
		if (MapClient.mapView.y < -1570)
			MapClient.mapView.y = -1570;
		mouse = e.getPoint();
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
		mouse = e.getPoint();
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
		repaint();
	}

	public void mouseExited(MouseEvent e) {
		repaint();
	}

	public void mousePressed(MouseEvent e) {
		MapClient.mapRegion = new Point(e.getX() - MapClient.mapView.x, e.getY() - MapClient.mapView.y);
		click = e.getPoint();
		int x = 2172 + (int) ((double) MapClient.mapRegion.x * 0.65789473683999999D);
		int y = 3971 - (int) ((double) MapClient.mapRegion.y * 0.65789473683999999D);
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.playerRights == 3) {
					c.sendMessage("Teleporting to: " + x + ", " + y + "");
					c.getPlayerAssistant().movePlayer(x, y, 0);
				}
			}
		}
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		click = new Point(-1, -1);
		repaint();
	}

	private Point mouse;
	public Point click;
}
