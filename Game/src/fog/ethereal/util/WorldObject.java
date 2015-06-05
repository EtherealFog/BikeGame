package fog.ethereal.util;

import java.util.List;

import fog.ethereal.world.Platform;

public interface WorldObject {
	public void update(List<Platform> platforms);
}
