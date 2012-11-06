#include <algorithm>

#include "sprite.h"

void SpriteBatch::destroySprite(unsigned int spriteId)
{
  Sprites::iterator sprIter = 
    std::find_if(m_sprites.begin(), m_sprites.end(), SpriteHasId(spriteId));
  if (sprIter != m_sprites.end())
  {
    SpriteInfo* sprite = *sprIter;
    m_sprites.erase(sprIter);
    delete sprite;
  }
}

void SpriteBatch::updateSpritePosition(unsigned int spriteId, Vec2 position)
{
  Sprites::iterator sprIter = 
    std::find_if(m_sprites.begin(), m_sprites.end(), SpriteHasId(spriteId));
  if (sprIter != m_sprites.end())
  {
    (*sprIter)->setPosition(position);
  }
}

void SpriteBatch::sort()
{
  std::sort(m_sprites.begin(), m_sprites.end(), SpriteCompare());
}
