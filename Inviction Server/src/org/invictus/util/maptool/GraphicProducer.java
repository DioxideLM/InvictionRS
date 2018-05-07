package org.invictus.util.maptool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GraphicProducer extends JPanel {

	private static final long serialVersionUID = 1L;

	public GraphicProducer(int width, int height) {
		graphics = null;
		this.width = 0;
		this.height = 0;
		this.width = width;
		this.height = height;
		graphics = null;
		setPreferredSize(new Dimension(this.width, this.height));
	}
	
	public void paintComponent(Graphics graphics) {
		this.graphics = (Graphics2D) graphics;
		this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(this.graphics);
		drawContent();
	}

	private void drawContent() {
		MapClient.mapImage.paintIcon(this, graphics, MapClient.mapView.x, MapClient.mapView.y);
		int x = 2172 + (int) ((double) MapClient.mapRegion.x * 0.65789473683999999D);
		int y = 3971 - (int) ((double) MapClient.mapRegion.y * 0.65789473683999999D);
		String data[] = { (new StringBuilder("Tile: ")).append(x).append(",").append(y).toString(), (new StringBuilder("Terrain: ")).append(x / 8).append(",").append(y / 8).toString() };
		graphics.setColor(new Color(0, 0, 0, 150));
		graphics.fillRect(10, height - 20 * data.length - 17, 200, 20 * data.length + 5);
		drawString(15, height - 20 * data.length, data);
		graphics.setColor(new Color(255, 0, 0, 100));
		graphics.drawLine((MapClient.mapRegion.x + MapClient.mapView.x) - 1, 0, (MapClient.mapRegion.x + MapClient.mapView.x) - 1, height);
		graphics.drawLine(0, (MapClient.mapRegion.y + MapClient.mapView.y) - 1, width, (MapClient.mapRegion.y + MapClient.mapView.y) - 1);
		graphics.drawLine(MapClient.mapRegion.x + MapClient.mapView.x + 1, 0, MapClient.mapRegion.x + MapClient.mapView.x + 1, height);
		graphics.drawLine(0, MapClient.mapRegion.y + MapClient.mapView.y + 1, width, MapClient.mapRegion.y + MapClient.mapView.y + 1);
		graphics.setColor(Color.RED);
		graphics.drawLine(MapClient.mapRegion.x + MapClient.mapView.x, 0, MapClient.mapRegion.x + MapClient.mapView.x, height);
		graphics.drawLine(0, MapClient.mapRegion.y + MapClient.mapView.y, width, MapClient.mapRegion.y + MapClient.mapView.y);
	}

	private void drawString(int x, int y, String str[]) {
		for (int i = 0; i < str.length; i++)
			drawString(str[i], x, y + i * 20);

	}

	private void drawString(String str, int x, int y) {
		graphics.setColor(new Color(0, 0, 0, 50));
		for (int i = x - 1; i <= x + 1; i++) {
			for (int i2 = y - 1; i2 <= y + 1; i2++)
				graphics.drawString(str, i, i2);

		}

		graphics.setColor(new Color(225, 255, 255));
		graphics.drawString(str, x, y);
	}

	private Graphics2D graphics;
	private int width;
	private int height;
}
