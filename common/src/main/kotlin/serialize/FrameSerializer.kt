package serialize

import Frame
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.serializersModuleOf
import org.koin.dsl.module

class FrameSerializer: Serializer<Frame> {

}