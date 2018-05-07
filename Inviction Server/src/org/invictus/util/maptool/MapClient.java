package org.invictus.util.maptool;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapClient extends Shell {

	private static final long serialVersionUID = 1L;

	public MapClient() {
		mapImage = getImageByName("map.png");
		setTitle("Region Tool");
		setVisible(true);
		setResizable(false);
		JPanel container = new JPanel();
		container.addMouseListener(this);
		container.addMouseMotionListener(this);
		container.setLayout(new BorderLayout(0, 0));
		container.add(new GraphicProducer(600, 400), "North");
		setContentPane(container);
		setDefaultCloseOperation(3);
		pack();
		setLocationRelativeTo(null);
	}

	public ImageIcon getImageByName(String name) {
		return new ImageIcon(getClass().getResource(name));
	}

	public static void main(String args[]) {
		new MapClient();
	}

	public static Point mapView = new Point(-1263, -973);
	public static Point mapRegion = new Point(1563, 1173);
	public static ImageIcon mapImage;

}
