package com.atguigu.gmall.mock.util

import scala.collection.mutable.ListBuffer

object RandomOptions {
  def apply[T](opts: (T, Int)*) = {
    val randomOptions = new RandomOptions[T]()
    //randomOptions.totalWeight = (0 /: opts) (_ + _._2) // 计算出来总的比重
    randomOptions.totalWeight = opts.foldLeft(0)((x,y) => x + y._2)
    opts.foreach {
      case (value, weight) => randomOptions.options ++= (1 to weight).map(_ => value)
    }
    randomOptions
  }
  def main(args: Array[String]): Unit = {
    for(x <- 1 to 5){
      val unit: RandomOptions[String] = RandomOptions[String](("zhangsan",3),("lisi",2))
      println(unit.getRandomOption())
    }
  }
}

class RandomOptions[T] {
  var totalWeight: Int = _
  var options = ListBuffer[T]()

  /**
   * 获取随机的 Option 的值
   *
   * @return
   */
  def getRandomOption() = {
    options(RandomNumUtil.randomInt(0, totalWeight - 1))
  }


}