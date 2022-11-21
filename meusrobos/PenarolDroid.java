package meusrobos;

import robocode.Droid;
import robocode.MessageEvent;
import robocode.TeamRobot;
import robocode.util.Utils;
import sampleteam.Point;
import sampleteam.RobotColors;
import robocode.*;

/*
 * Um Droid é uma interface usada em um TeamRobot para criar um robô especializado de time, por exemplo: um robô droide. 
 * Um droide tem 20 extra life/energia, mas vêm com um custo que é que o droide não tem scanner!
 * Isto significa que o droid é 100% dependente de outros membros do time para realizar o escanemaento e comunicar a localização de inimigos.
 * 
 * Um time de droides adicionalmente consiste de um robô que não é droide que possa ter vantagem 
 * Uma equipe de droides junto com pelo menos um robô de equipe não-droide pode ter uma vantagem crucial sobre outra equipe sem droides (e o mesmo número de robôs)
 * devido aos 20 pontos de energia adicionais. 
 */

public class PenarolDroid extends TeamRobot implements Droid
{
	public void run()
	{
		out.println("PenarolDroid pronto!");

		// Comportamento padrão e repetitivo
		while (true)
		{
			ahead(200);
			back(200);
		}
	}

	/**
	 * onMessageReceived: O que fazer quando nosso líder nos manda uma mensagem
	 */
	public void onMessageReceived(MessageEvent e)
	{
		// Atirar neste ponto (ponto recebido pelo nosso Líder para que atiremos nele!)
		if (e.getMessage() instanceof Point)
		{
			Point p = (Point) e.getMessage();

			// Calculando x e y para mirar
			double dx = p.getX() - this.getX();
			double dy = p.getY() - this.getY();
			
			// Calculando o ângulo para mirar
			double theta = Math.toDegrees(Math.atan2(dx, dy));
			
			// Rotacionando a arma para apontar
			turnGunRight(Utils.normalNearAbsoluteAngleDegrees(theta - getGunHeading()));

			// Atira pra valer!
			fire(3);
		}
		else if (e.getMessage() instanceof RobotColors)
		{
			// Settas as cores dos nossos robôs Droides, recebidas do líder do time
			RobotColors teamStyle = (RobotColors) e.getMessage();

			setBodyColor(teamStyle.bodyColor);
			setGunColor(teamStyle.gunColor);
			setRadarColor(teamStyle.radarColor);
			setScanColor(teamStyle.scanColor);
			setBulletColor(teamStyle.bulletColor);
		}
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(90 - e.getBearing());
	}
}
