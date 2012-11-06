#ifndef SPRITE_H
#define SPRITE_H

#include <vector>

#include "log_macros.h"
#include "vec2.h"

class SpriteInfo
{
public:
  SpriteInfo(unsigned int id, unsigned int textureRef, Vec2 position) 
   : m_id(id), 
     m_textureRef(textureRef),
     m_position(position)
  {}

  ~SpriteInfo()
  {
    LOGI("Destroying sprite with id=%d", m_id);
  }

  inline void setDistance(float distance) { m_distance = distance; }
  inline float distance() const { return m_distance; }

  inline unsigned int id() const { return m_id; }

  inline void setPosition(Vec2 pos) { m_position = pos; }
  inline Vec2 position() const { return m_position; }

  inline unsigned int textureRef() const { return m_textureRef; }
private:
  unsigned int m_id;
  unsigned int m_textureRef;  // Key to textureTable.
  float m_distance; // Distance from camera.
  Vec2 m_position;
};

class SpriteBatch
{
  struct SpriteHasId
  {
    SpriteHasId(unsigned int id) : m_id(id)
    {}

    bool operator()(SpriteInfo* sprite)
    {
      return sprite->id() == m_id;
    }

    unsigned int m_id;
  };

  struct SpriteCompare
  {
    bool operator()(SpriteInfo* left, SpriteInfo* right)
    {
      // Sort so objects far from the camera are drawn first.
      return left->distance() > right->distance();
    }
  };
public:
  void addSprite(SpriteInfo* sprite)
  {
    m_sprites.push_back(sprite);
  }

  void destroySprite(unsigned int spriteId);

  void updateSpritePosition(unsigned int spriteId, Vec2 position);

  void updateSpriteDistances(float origX, float origY);

  void sort();

  inline SpriteInfo* getSpriteAtIndex(size_t index) const { return m_sprites[index]; }

  inline size_t size() const { return m_sprites.size(); }
private:
  typedef std::vector<SpriteInfo*> Sprites;
  Sprites m_sprites;
};

#endif
