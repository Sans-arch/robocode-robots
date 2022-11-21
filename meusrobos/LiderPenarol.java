package meusrobos;

import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import java.awt.*;
import java.io.IOException;
import sampleteam.Point;
import sampleteam.RobotColors;

/**
 * LiderPenarol - robô líder do time de robôs Penarol.
 * 
 * Busca ao redor por inimigos, e ordena companheiros a atirarem.
 */
public class LiderPenarol extends TeamRobot
{

	// run: Comportamento padrão do Líder
	public void run()
	{
		// Prepara um objeto RobotColors
		RobotColors teamStyle = new RobotColors();

		teamStyle.bodyColor = Color.yellow;
		teamStyle.gunColor = Color.black;
		teamStyle.radarColor = Color.yellow;
		teamStyle.scanColor = Color.yellow;
		teamStyle.bulletColor = Color.red;

		// Setta a cor deste robô contendo os RobotColors
		setBodyColor(teamStyle.bodyColor);
		setGunColor(teamStyle.gunColor);
		setRadarColor(teamStyle.radarColor);
		setScanColor(teamStyle.scanColor);
		setBulletColor(teamStyle.bulletColor);

		try 
		{
			// Manda o objeto RobotColors para nosso time inteiro
			broadcastMessage(teamStyle);
		} 
		catch (IOException ignored) 
		{
		}
		
		// Comportamento padrão
		while (true)
		{
			setTurnRadarRight(10000);
			ahead(100);
			back(100);
		}
	}

	// onScannedRobot: O que fazer quando ele você vê outro robô
	public void onScannedRobot(ScannedRobotEvent e) {

		// Não atirar se o robô avistado for um companheiro
		if (isTeammate(e.getName()))
		{
			return;
		}

		// Calculate enemy bearing
		double enemyBearing = this.getHeading() + e.getBearing();

		// Calculate enemy's position
		double enemyX = getX() + e.getDistance() * Math.sin(Math.toRadians(enemyBearing));
		double enemyY = getY() + e.getDistance() * Math.cos(Math.toRadians(enemyBearing));

		try
		{
			// Mandar a posição do inimigo para nossos companheiros
			broadcastMessage(new Point(enemyX, enemyY));
		}
		catch (IOException ex)
		{
			out.println("Nao eh possivel enviar a ordem: ");
			ex.printStackTrace(out);
		}
	}

	/**
	 * onHitByBullet:  Turn perpendicular to bullet path
	 */
	public void onHitByBullet(HitByBulletEvent e)
	{
		turnLeft(90 - e.getBearing());
	}
}
