package scala.cli.directivehandler

final case class ScopePath(
  root: Either[String, os.Path],
  subPath: os.SubPath
) {
  def /(subPath: os.PathChunk): ScopePath =
    copy(subPath = this.subPath / subPath)
  def path: Either[String, os.Path] =
    root
      .left.map(r => s"$r/$subPath")
      .map(_ / subPath)
}

object ScopePath {
  def fromPath(path: os.Path): ScopePath = {
    def root(p: os.Path): os.Path =
      if (p.segmentCount > 0) root(p / os.up) else p
    val root0 = root(path)
    ScopePath(Right(root0), path.subRelativeTo(root0))
  }
}
