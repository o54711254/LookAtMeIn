class SingletonModel:
    __instance = None
    __model = None
    def __new__(cls, *args, **kwargs):
        if not SingletonModel.__instance:
            SingletonModel.__instance = object.__new__(cls)
        return SingletonModel.__instance

    def __init__(self, model):
        self.__model = model

    def getModel(self):
        return self.__model
    
    @staticmethod
    def getInstance():
        if not SingletonModel.__instance:
            raise ValueError("SingletonModel is not initialized")
        else:
            return SingletonModel.__instance